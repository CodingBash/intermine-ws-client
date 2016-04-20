package org.intermine.webservice.client.runners;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.intermine.metadata.Model;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.OrderDirection;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.QueryService;

// Service request. Not SOAP?
/* 
 
	<query name="" model="genomic" view="Gene.transcripts.primaryIdentifier" longDescription="" sortOrder="Gene.transcripts.primaryIdentifier asc">
  		<constraint path="Gene" op="LOOKUP" value="E2f1" extraValue="D. melanogaster"/>
	</query>

 */
/**
 * Gets following information about Gene E2F1 D melanogaster from service
 * 
 * @gene e2f1
 * @organism D melanogaster
 * @views Gene.transcripts.primaryIdentifier
 * 
 * @serviceExpectation 6x1 rows
 * 
 *
 * 
 */
public class GeneE2F1DmelanogasterGeneTranscriptsQueryClient {
	private static final String ROOT = "http://www.flymine.org/flymine/service";

	public static void main(String[] args) throws IOException {
		ServiceFactory factory = new ServiceFactory(ROOT);
		Model model = factory.getModel();
		PathQuery query = new PathQuery(model);

		// Select the output columns:
		query.addView("Gene.transcripts.primaryIdentifier");

		// Add orderby
		query.addOrderBy("Gene.transcripts.primaryIdentifier", OrderDirection.ASC);

		// Filter the results with the following constraints:
		query.addConstraint(Constraints.lookup("Gene", "E2f1", "D. melanogaster"));

		QueryService service = factory.getQueryService();
		PrintStream out = System.out;
		out.println("Gene.transcripts.primaryIdentifier");
		Iterator<List<Object>> rows = service.getRowListIterator(query);
		while (rows.hasNext()) {
			out.println(rows.next().get(0));
		}
		out.printf("%d rows\n", service.getCount(query));
	}

}