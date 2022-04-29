package Triangle.SyntacticAnalyzer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// Agregado por Miguel Mesen
public class HtmlToken {

    private Scanner lexicalAnalyser;
    private Token currentToken;

    public HtmlToken(Scanner lexer) {
        lexicalAnalyser = lexer;
    }

    public Document print(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element html = document.createElement("html");
            Element head = document.createElement("head");
            Element title = document.createElement("title");
            Element body = document.createElement("body");
            Element paragraph =  document.createElement("pre");
            html.setAttribute("lang", "en");
            title.setTextContent("Código fuente");
            head.appendChild(title);
            html.appendChild(head);

            currentToken = lexicalAnalyser.scanCodeToHtml();
            while (currentToken.kind != Token.EOT) {
                if (currentToken.kind == Token.IDENTIFIER ||
                        currentToken.kind == Token.OPERATOR ||
                        currentToken.kind > 31){
                    Element ele = document.createElement("font");
                    ele.setAttribute("color","Black");
                    ele.setAttribute("size","3");
                    ele.setAttribute("face","Courier New");
                    ele.setTextContent(currentToken.spelling);
                    paragraph.appendChild(ele);

                }
                else if(currentToken.kind == Token.CHARLITERAL ||
                        currentToken.kind == Token.INTLITERAL){
                    Element ele = document.createElement("font");
                    ele.setAttribute("color","Blue");
                    ele.setAttribute("size","3");
                    ele.setAttribute("face","Courier New");
                    ele.setTextContent(currentToken.spelling);
                    paragraph.appendChild(ele);

                }
                else if(currentToken.kind == -1){
                    Element ele = document.createElement("font");
                    ele.setAttribute("color","Green");
                    ele.setAttribute("size","3");
                    ele.setAttribute("face","Courier New");
                    ele.setTextContent(currentToken.spelling);
                    paragraph.appendChild(ele);

                }
                else if(currentToken.kind == -2){
                    Element ele = document.createElement("font");
                    if(currentToken.spelling.length()==1){
                        ele.setTextContent(" ");

                    }
                    else{
                        ele.setTextContent("    ");

                    }
                    paragraph.appendChild(ele);

                }
                else if(currentToken.kind == -3){
                    Element ele = document.createElement("font");
                    ele.setTextContent("\n");
                    paragraph.appendChild(ele);

                }
                else{
                    Element ele = document.createElement("font");
                    Element negrita = document.createElement("b");
                    negrita.setTextContent(currentToken.spelling);
                    ele.setAttribute("color","Black");
                    ele.setAttribute("size","3");
                    ele.setAttribute("face","Courier New");
                    ele.appendChild(negrita);
                    paragraph.appendChild(ele);

                }
                currentToken = lexicalAnalyser.scanCodeToHtml();
            }
            body.appendChild(paragraph);
            html.appendChild(body);
            document.appendChild(html);
            return document;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(HtmlToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
// -------------------------