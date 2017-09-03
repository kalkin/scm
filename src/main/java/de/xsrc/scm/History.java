package de.xsrc.scm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * This is an {@link Iterable} which is used by the UI for iterating over
 * {@link HistoryEntry}s
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class History implements Iterable<HistoryEntry> {

    private final Iterator<HistoryEntry> iterator;
    private Repository repo;

    /**
     * Generate History for the current directory
     *
     * @param uri
     *            local or remote path to a git repo
     * @throws IOException
     *             Git repository can not be opened
     */
    public History(final String uri) throws IOException {
	File current = new File(uri);
	FileRepositoryBuilder builder = new FileRepositoryBuilder().findGitDir(current);
	this.repo = builder.build();
	this.iterator = new HistoryIterator(this.repo);
    }

    @Override
    public Iterator<HistoryEntry> iterator() {
	return this.iterator;
    }

}
