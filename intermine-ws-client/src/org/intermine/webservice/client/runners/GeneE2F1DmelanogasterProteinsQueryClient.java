package org.intermine.webservice.client.runners;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.intermine.metadata.Model;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.OuterJoinStatus;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.QueryService;

/**
 * Calls service for proteins in Gene: E2F1 D melanogaster
 * 
 * @serviceExpectation 3x3 of proteins
 *
 */
public class GeneE2F1DmelanogasterProteinsQueryClient {
	private static final String ROOT = "http://www.flymine.org/flymine/service";

	public static void main(String[] args) throws IOException {
		ServiceFactory factory = new ServiceFactory(ROOT);
		Model model = factory.getModel();
		PathQuery query = new PathQuery(model);

		// Select the output columns:
		query.addViews("Gene.proteins.primaryIdentifier", "Gene.proteins.primaryAccession",
				"Gene.proteins.organism.name");

		// Filter the results with the following constraints:
		query.addConstraint(Constraints.eq("Gene.id", "1071960"));

		// Outer Joins
		// Show all information about these relationships if they exist, but do
		// not require that they exist.
		query.setOuterJoinStatus("Gene.proteins.organism", OuterJoinStatus.OUTER);

		QueryService service = factory.getQueryService();
		PrintStream out = System.out;
		String format = "%-30.30s | %-30.30s | %-30.30s\n";
		out.printf(format, query.getView().toArray());
		Iterator<List<Object>> rows = service.getRowListIterator(query);
		while (rows.hasNext()) {
			out.printf(format, rows.next().toArray());
		}
		out.printf("%d rows\n", service.getCount(query));
	}

}