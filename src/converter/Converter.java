/**
 * 
 */
package converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Strings;

/**
 * @author Sanjay
 *
 */
public class Converter {
	private String delimiter = "\t";
	private String newline = "\n";
	private int table_headers_row = 0;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Converter converter = new Converter();
		String tsvFile = "data/research_papers.tsv";
		converter.convertTSVtoHTMLTable(tsvFile);
	}

	public String convertTSVtoHTMLTable(String tsvFile) throws IOException {

		File file = new File(tsvFile);
		List<String> lines = FileUtils.readLines(file, "UTF-8");

		List<String> tableHeaders = new ArrayList<>();
		List<List<String>> dataRows = new ArrayList<>();
		int counter = 0;
		for (String string : lines) {

			if (!Strings.isNullOrEmpty(string)) {
				// table header
				if (counter == table_headers_row) {

					String[] headers = string.split(delimiter);
					tableHeaders = Arrays.asList(headers);
				}

				// data rows;
				else {
					String[] columns = string.split(delimiter);
					for (int i = 0; i < columns.length; i++) {
						String string2=columns[i];
						string2=string2.replace("\"", "");
						columns[i]=string2;
					}
					
					dataRows.add(Arrays.asList(columns));
					
				}

				counter++;
			}
		}

		StringBuilder builder = buildHTMLTable(tableHeaders, dataRows);
		System.out.println(builder);

		return builder.toString();

	}

	private StringBuilder buildHTMLTable(List<String> tableHeaders, List<List<String>> dataRows) {
		StringBuilder tableBuilder = new StringBuilder();

		String tableDIV = "<table id='reserachPapersTable' class='table table-bordered'>";

	
		String tableEnd = "</table>";
		String tableHeadBegin = "<thead>";
		String tableHeadEnd = "</thead>";
		String thEnd = "</th>";
		String thBegin = "<th>";
		
		
		
		
		String trBegin = "<tr>";
		String trEnd = "</tr>";
		String tdBegin = "<td>";
		String tdEnd = "</td>";
		String tBodyBegin = "<tbody>";
		String tBodyEnd = "</tbody>";

		//link columns
		
		int title_column=0;
		int link_column=1;
		
		tableBuilder.append(tableDIV + newline);

		// add table headers
		tableBuilder.append(tableHeadBegin + newline);

		// add table columns
		for (int i = 0; i < tableHeaders.size(); i++) {
			
			
			if(i!=link_column){
				tableBuilder.append(thBegin + tableHeaders.get(i) + thEnd + newline);
			}
		}
		
		tableBuilder.append(tableHeadEnd + newline);

		// add data rows
		tableBuilder.append(tBodyBegin + newline);
		for (List<String> list : dataRows) {
			tableBuilder.append(trBegin + newline);
			
			
			for (int i = 0; i < list.size(); i++) {
				String dataColumn=list.get(i);
			
				//we do this only for adding link to paper title
				if(i==title_column){
					String url=list.get(link_column);
					String link="<a href='" + url + "'>" + dataColumn + "</a>" ;
					tableBuilder.append(tdBegin + link + tdEnd + newline);
				}
				else if(i==link_column){
					
				}
				else{
					tableBuilder.append(tdBegin + dataColumn + tdEnd + newline);
				}
				
			}
			tableBuilder.append(trEnd + newline);
		}
		tableBuilder.append(tBodyEnd + newline);
		tableBuilder.append(tableEnd + newline);
		return tableBuilder;

	}

}
