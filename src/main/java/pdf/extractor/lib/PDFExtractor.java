package pdf.extractor.lib;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The class which takes care of taking an input file, and creating a new PDF with given pages.
 *
 * @author Nikhilesh
 */
public class PDFExtractor {

    /**
     * Creates a single new PDF file from the given input file location. The new PDF is created in the output file
     * location and contains only the given list of pages.
     *
     * @param inputFile Complete path of File to be split
     * @param listOfPages list of pages to extract from input file
     * @param outputFileName Complete location of output File.
     * @return a boolean value indicating whether new PDF was created successfully or not.
     */
    public boolean createNewPDF(final Path inputFile, final List<Integer> listOfPages, final Path
            outputFileName) {

        /*
         * If input file doesn't exist return the given list of pages as unprocessed.
         */
        if(!Files.exists(inputFile)){
            return false;
        }
        try {
            PDDocument inputPDF = PDDocument.load(inputFile.toFile());
            PDDocument outputPDF = new PDDocument();
            //Input page number will be 1-based, whereas code works on 0-based
            listOfPages.forEach(pageNumber -> outputPDF.addPage(inputPDF.getPage(--pageNumber)));

            outputPDF.save(outputFileName.toFile());
            outputPDF.close();
            }catch(Exception e){
                // Catch any exception and throw false
                e.printStackTrace();
                return false;

        }
        return true;
    }
}
