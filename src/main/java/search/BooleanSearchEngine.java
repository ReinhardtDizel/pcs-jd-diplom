package search;

import doument.AnyDocument;
import reader.DocumentsReader;
import reader.PdfDocumentsReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

public class BooleanSearchEngine implements SearchEngine {

    private final List<AnyDocument> documents = new ArrayList<>();
    private final DocumentsReader reader = new PdfDocumentsReader();
    private IndexingDocuments indexingDocuments;

    public BooleanSearchEngine(File pdfsDir) {
        readDocumentsDir(pdfsDir);
        indexingDocuments = new IndexingDocuments(documents);
        indexingDocuments.indexDocuments();
    }

    @Override
    public List<PageEntry> search(String word) {
        return indexingDocuments.searchWord(word);
    }

    public void readDocumentsDir(File directory) {
        File[] fileList = directory.listFiles();
        Objects.requireNonNull(fileList, String.format("В директории %s не обнаружено файлов", directory));
        Arrays.stream(fileList).forEach(file -> documents.add(reader.read(file)));
    }
}
