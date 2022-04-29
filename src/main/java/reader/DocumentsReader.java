package reader;

import doument.AnyDocument;

import java.io.File;

public interface DocumentsReader {

    AnyDocument read(File file);
}
