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
 * Get's following information about Gene E2F1 of D melanogaster for service:
 * 
 * "Gene.cytoLocation", "Gene.secondaryIdentifier", "Gene.symbol",
 * "Gene.primaryIdentifier", "Gene.organism.name",
 * "Gene.alleles.primaryIdentifier", "Gene.alleles.symbol",
 * "Gene.alleles.alleleClass", "Gene.alleles.organism.name",
 * "Gene.CDSs.primaryIdentifier", "Gene.CDSs.symbol",
 * "Gene.CDSs.gene.primaryIdentifier", "Gene.CDSs.organism.name",
 * "Gene.childFeatures.primaryIdentifier", "Gene.childFeatures.organism.name",
 * "Gene.miRNAtargets.primaryIdentifier", "Gene.miRNAtargets.organism.name",
 * "Gene.mRNAExpressionResults.stageRange",
 * "Gene.mRNAExpressionResults.expressed", "Gene.sequence.length",
 * "Gene.transcripts.primaryIdentifier"
 * 
 * @deprecated 0 results
 */
public class GeneE2F1DmelanogasterGeneQueryClient {
	private static final String ROOT = "http://www.flymine.org/flymine/service";

	/**
	 * Perform the query and print the rows of results.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServiceFactory factory = new ServiceFactory(ROOT);
		Model model = factory.getModel();
		PathQuery query = new PathQuery(model);

		// Select the output columns:
		query.addViews("Gene.cytoLocation", "Gene.secondaryIdentifier", "Gene.symbol", "Gene.primaryIdentifier",
				"Gene.organism.name", "Gene.alleles.primaryIdentifier", "Gene.alleles.symbol",
				"Gene.alleles.alleleClass", "Gene.alleles.organism.name", "Gene.CDSs.primaryIdentifier",
				"Gene.CDSs.symbol", "Gene.CDSs.gene.primaryIdentifier", "Gene.CDSs.organism.name",
				"Gene.childFeatures.primaryIdentifier", "Gene.childFeatures.organism.name",
				"Gene.miRNAtargets.primaryIdentifier", "Gene.miRNAtargets.organism.name",
				"Gene.mRNAExpressionResults.stageRange", "Gene.mRNAExpressionResults.expressed", "Gene.sequence.length",
				"Gene.transcripts.primaryIdentifier");

		// Add orderby
		query.addOrderBy("Gene.cytoLocation", OrderDirection.ASC);

		// Filter the results with the following constraints:
		query.addConstraint(Constraints.lookup("Gene", "E2f1", "D. melanogaster"));

		QueryService service = factory.getQueryService();
		PrintStream out = System.out;
		String format = "%-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s | %-10.10s\n";
		out.printf(format, query.getView().toArray());
		Iterator<List<Object>> rows = service.getRowListIterator(query);
		while (rows.hasNext()) {
			out.printf(format, rows.next().toArray());
		}
		out.printf("%d rows\n", service.getCount(query));
	}

}