package org.intermine.webservice.client.runners;

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
 * It was automatically generated at Mon Apr 18 23:14:35 BST 2016
 *
 * @author FlyMine
 *
 */
public class QueryClient
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
        query.addViews("DataSet.name",
                "DataSet.url");

        // Add orderby
        query.addOrderBy("DataSet.name", OrderDirection.ASC);

        QueryService service = factory.getQueryService();
        PrintStream out = System.out;
        String format = "%-47.47s | %-47.47s\n";
        out.printf(format, query.getView().toArray());
        Iterator<List<Object>> rows = service.getRowListIterator(query);
        while (rows.hasNext()) {
            out.printf(format, rows.next().toArray());
        }
        out.printf("%d rows\n", service.getCount(query));
    }

}
