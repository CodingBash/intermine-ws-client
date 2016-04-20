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

/**
 * 
 * Calls service for transcription factor in Gene: E2f1 D. melanogaster
 * 
 * @see http://www.flymine.org/flymine/report.do?id=1071960#
 * 
 * @deprecated Service returnrs no rows
 *
 */
public class GeneE2F1DmelanogasterTranscriptionFactorQueryClient {
	private static final String ROOT = "http://www.flymine.org/flymine/service";

	public static void main(String[] args) throws IOException {
		ServiceFactory factory = new ServiceFactory(ROOT);
		Model model = factory.getModel();
		PathQuery query = new PathQuery(model);

		// Select the output columns:
		query.addViews("Gene.regulatoryRegions.factor.secondaryIdentifier", "Gene.regulatoryRegions.factor.symbol",
				"Gene.secondaryIdentifier", "Gene.symbol", "Gene.chromosome.primaryIdentifier",
				"Gene.chromosomeLocation.start", "Gene.chromosomeLocation.end", "Gene.chromosomeLocation.strand");

		// Add orderby
		query.addOrderBy("Gene.regulatoryRegions.factor.secondaryIdentifier", OrderDirection.ASC);

		// Filter the results with the following constraints:
		query.addConstraint(Constraints.type("Gene.regulatoryRegions", "TFBindingSite"));
		query.addConstraint(Constraints.eq("Gene.regulatoryRegions.factor.id", "1071960"), "A");

		QueryService service = factory.getQueryService();
		PrintStream out = System.out;
		String format = "%-12.12s | %-12.12s | %-12.12s | %-12.12s | %-12.12s | %-12.12s | %-12.12s | %-12.12s\n";
		out.printf(format, query.getView().toArray());
		Iterator<List<Object>> rows = service.getRowListIterator(query);
		while (rows.hasNext()) {
			out.printf(format, rows.next().toArray());
		}
		out.printf("%d rows\n", service.getCount(query));
	}

}