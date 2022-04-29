package search;

import doument.AnyDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IndexingDocuments {

    private final List<AnyDocument> documents;
    private final Map<String, List<PageEntry>> pageEntryMap = new HashMap<>();

    public IndexingDocuments(List<AnyDocument> documents) {
        this.documents = documents;
    }

    public List<PageEntry> searchWord(String word) {
        if (pageEntryMap.containsKey(word)) {
            List<PageEntry> res = pageEntryMap.get(word);
            Collections.reverse(res);
            return res;
        }
        return null;
    }

    public void indexDocuments() {
        for (AnyDocument document : documents) {
            for (int i = 1; i < document.getPageCount(); ++i) {
                indexingPage(document.getPage(i), i, document.getName());
            }
        }
    }

    private void indexingPage(String page, int pageCount, String fileName) {

        Arrays.stream(page.split("\\P{IsAlphabetic}+"))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<String, Long>comparingByValue())
                .forEachOrdered(entry -> {
                    setEntryList(entry.getKey(), new PageEntry(fileName, pageCount, entry.getValue().intValue()));
                });

    }

    private void setEntryList(String word, PageEntry entry) {
        if (pageEntryMap.containsKey(word)) {
            pageEntryMap.get(word).add(entry);
        } else {
            List<PageEntry> entryList = new ArrayList<>();
            entryList.add(entry);
            pageEntryMap.put(word, entryList);
        }
    }

}
