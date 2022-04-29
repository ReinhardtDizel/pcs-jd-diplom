package reader;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import doument.AnyDocument;
import doument.AnyDocumentPdf;

import java.io.File;
import java.io.IOException;

public class PdfDocumentsReader implements DocumentsReader {

    @Override
    public AnyDocument read(File file) {
        try (PdfDocument documentPdf = new PdfDocument(new PdfReader(file))) {
            return new AnyDocumentPdf(documentPdf, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
