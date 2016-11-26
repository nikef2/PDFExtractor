package pdf.extractor.lib;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The class which takes care of taking an input file, and creating a new PDF with given pages.
 * Created by nike on 24/11/16.
 */
public class PDFExtractor {

    /**
     * Creates a single new PDF file from the given input file location. The new PDF is created in the output file
     * location and contains only the given list of pages.
     *
     * @param inputFile Complete path of File to be split
     * @param listOfPages list of pages to extract from input file
     * @param outputFileName Complete location of output File.
     * @return a list of integers of page numbers if any pages were not processed
     */
    public List<Integer> createNewPDF(final Path inputFile, final List<Integer> listOfPages, final Path
            outputFileName){
        List<Integer> pagesNotProcessed = new ArrayList<>();
        return pagesNotProcessed;
    }
}
