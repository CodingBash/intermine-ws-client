package org.intermine.webservice.client.runners;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.intermine.metadata.Model;
import org.intermine.pathquery.OrderDirection;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.QueryService;

/**
 * This is a Java program to run a query from FlyMine.
 * It was automatically generated at Mon Apr 18 23:18:35 BST 2016
 *
 * @author FlyMine
 *
 */
public class TissueQueryClient
{
    private static final String ROOT = "http://www.flymine.org/flymine/service";

    /**
     * Perform the query and print the rows of results.
     * @param args command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServiceFactory factory = new ServiceFactory(ROOT);
        Model model = factory.getModel();
        PathQuery query = new PathQuery(model);

        // Select the output columns:
        query.addViews("Tissue.name",
                "Tissue.expressionResults.affyCall",
                "Tissue.expressionResults.enrichment",
                "Tissue.expressionResults.mRNASignal",
                "Tissue.expressionResults.mRNASignalSEM",
                "Tissue.expressionResults.presentCall");

        // Add orderby
        query.addOrderBy("Tissue.name", OrderDirection.ASC);

        QueryService service = factory.getQueryService();
        PrintStream sout = System.out;
        PrintStream fout = new PrintStream(new File("tissueResult.txt"));
        PrintStream out = fout;
        String format = "%-13.13s | %-13.13s | %-13.13s | %-13.13s | %-13.13s | %-13.13s\n";
        out.printf(format, query.getView().toArray());
        Iterator<List<Object>> rows = service.getRowListIterator(query);
        while (rows.hasNext()) {
            out.printf(format, rows.next().toArray());
        }
        out.printf("%d rows\n", service.getCount(query));
    }

}