/**
 *
 */
package de.xsrc.scm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 */
public final class HistoryIterator implements Iterator<HistoryEntry> {
    private static final Logger LOG = LoggerFactory.getLogger(HistoryIterator.class);

    private final HashMap<Iterator<RevCommit>, HistoryEntry> walkerHistoryMap = new HashMap<>();
    private final LinkedList<Iterator<RevCommit>> walkerIterators = new LinkedList<>();

    public HistoryIterator(final Repository repo) {
	final RevWalk walker = new RevWalk(repo);
	try {
	    walker.markStart(walker.parseCommit(repo.resolve("HEAD")));
	} catch (RevisionSyntaxException | IOException e) {
	    LOG.error("RevWalk could not mark start " + repo.getDirectory().getAbsolutePath());
	    e.printStackTrace();
	} finally {
	    walker.close();
	}
	this.walkerIterators.add(walker.iterator());
	initSubmodule(repo);
    }

    @Override
    public boolean hasNext() {
	for (final Iterator<RevCommit> i : this.walkerIterators) {
	    if (i.hasNext())
		return true;
	}
	return false;
    }

    /**
     */
    private void initSubmodule(final Repository repo) {
	try {
	    final SubmoduleWalk subWalk = SubmoduleWalk.forIndex(repo);
	    while (subWalk.next()) {
		final Repository subRepo = subWalk.getRepository();
		final RevWalk walker = new RevWalk(subRepo);
		walker.markStart(walker.parseCommit(subRepo.resolve("HEAD")));
		this.walkerIterators.add(walker.iterator());
		walker.close();
	    }

	} catch (final IOException e) {
	    LOG.error("Can not iterate through submodules " + repo.getDirectory().getAbsolutePath());
	    e.printStackTrace();
	}
    }

    @Override
    public HistoryEntry next() {
	recomputeMap();
	Iterator<RevCommit> youngest = null;
	for (final Iterator<RevCommit> i : this.walkerHistoryMap.keySet()) {
	    if (youngest == null) {
		youngest = i;
	    } else {
		final HistoryEntry young = this.walkerHistoryMap.get(youngest);
		final HistoryEntry candid = this.walkerHistoryMap.get(i);
		if (candid.getDate() > young.getDate()) {
		    youngest = i;
		}
	    }
	}
	if (youngest == null)
	    return null;
	else
	    return this.walkerHistoryMap.remove(youngest);
    }

    /**
     *
     */
    private void recomputeMap() {
	for (final Iterator<RevCommit> iterator : this.walkerIterators) {
	    this.walkerHistoryMap.computeIfAbsent(iterator, i -> {
		if (i.hasNext()) {
		    final HistoryEntry historyEntry = new HistoryEntry(i.next());
		    return historyEntry;
		}
		return null;
	    });
	}
    }

}
