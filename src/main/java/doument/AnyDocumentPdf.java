package doument;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.util.ArrayList;
import java.util.List;

public class AnyDocumentPdf implements AnyDocument {

    private final String name;
    private final int pageCount;
    private final PdfDocument document;
    private final List<String> documentList = new ArrayList<>();

    public AnyDocumentPdf(PdfDocument document, String name) {
        this.document = document;
        this.name = name;
        this.pageCount = document.getNumberOfPages();
        setPageList();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String getPage(int page) {
        return documentList.get(page - 1);
    }

    private void setPageList() {
        for (int i = 1; i <= pageCount; ++i) {
            documentList.add(PdfTextExtractor.getTextFromPage(document.getPage(i)));
        }
    }
}
