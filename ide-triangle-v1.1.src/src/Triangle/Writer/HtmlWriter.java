package Triangle.Writer;

import Triangle.SyntacticAnalyzer.Scanner;
import Triangle.SyntacticAnalyzer.SourceFile;
import Triangle.SyntacticAnalyzer.HtmlToken;
import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

// Agregado por Miguel Mesen
public class HtmlWriter {


    public HtmlWriter() {

    }


    public void write(String sourceName){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            SourceFile sourceFile = new SourceFile(sourceName);
            Scanner scanner = new Scanner(sourceFile);
            HtmlToken htmlToken = new HtmlToken(scanner);
            Document document = htmlToken.print();
            DOMSource domSource = new DOMSource(document);
            String sourceNameHtml = sourceName.replace(".tri", ".html");
            StreamResult streamResult = new StreamResult(new File(sourceNameHtml));
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
// -------------------------