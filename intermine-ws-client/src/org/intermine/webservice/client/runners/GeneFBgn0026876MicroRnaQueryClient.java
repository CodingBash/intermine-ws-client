package org.intermine.webservice.client.runners;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.intermine.metadata.Model;
import org.intermine.pathquery.Constraints;
import org.intermine.pathquery.OrderDirection;
import org.intermine.pathquery.OuterJoinStatus;
import org.intermine.pathquery.PathQuery;
import org.intermine.webservice.client.core.ServiceFactory;
import org.intermine.webservice.client.services.QueryService;

/**
 * For a specified gene show any microRNA genes that are known to target it.
 * (DataSet: miRBase).
 * 
 * @gene FBgn0026876
 * @organism ambiguous
 * @action gene -> microRNA
 * @actionUrl http://www.flymine.org/flymine/template.do?name=
 *            miRNAtargetGene_miRNAgene&scope=all
 * @publication http://www.ncbi.nlm.nih.gov/pubmed/14709173
 * @expectedResult 5x6
 * 
 *
 */
public class GeneFBgn0026876MicroRnaQueryClient {
	private static final String ROOT = "http://www.flymine.org/flymine/service";

	public static void main(String[] args) throws IOException {
		ServiceFactory factory = new ServiceFactory(ROOT);
		Model model = factory.getModel();
		PathQuery query = new PathQuery(model);

		// Select the output columns:
		query.addViews("Gene.miRNAtargets.target.gene.secondaryIdentifier", "Gene.miRNAtargets.target.gene.symbol",
				"Gene.miRNAtargets.target.primaryIdentifier", "Gene.symbol", "Gene.miRNAtargets.pvalue",
				"Gene.miRNAtargets.dataSets.name");

		// Add orderby
		query.addOrderBy("Gene.miRNAtargets.target.gene.secondaryIdentifier", OrderDirection.ASC);

		// Filter the results with the following constraints:
		query.addConstraint(Constraints.eq("Gene.organism.name", "Drosophila melanogaster"), "B");
		query.addConstraint(Constraints.lookup("Gene.miRNAtargets.target.gene", "FBgn0026876", "D. melanogaster"), "A");
		// Specify how these constraints should be combined.
		query.setConstraintLogic("A and B");

		// Outer Joins
		// Show all information about these relationships if they exist, but do
		// not require that they exist.
		query.setOuterJoinStatus("Gene.miRNAtargets.dataSets", OuterJoinStatus.OUTER);

		QueryService service = factory.getQueryService();
		PrintStream out = System.out;
		String format = "%-13.13s | %-13.13s | %-13.13s | %-13.13s | %-13.13s | %-13.13s\n";
		out.printf(format, query.getView().toArray());
		Iterator<List<Object>> rows = service.getRowListIterator(query);
		while (rows.hasNext()) {
			out.printf(format, rows.next().toArray());
		}
		out.printf("%d rows\n", service.getCount(query));
	}

}