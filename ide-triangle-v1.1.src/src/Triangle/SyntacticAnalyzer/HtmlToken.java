package Triangle.SyntacticAnalyzer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


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
                        currentToken.kind > 29){
                    Element var = document.createElement("font");
                    var.setAttribute("color","Black");
                    var.setAttribute("size","3");
                    var.setAttribute("face","Courier");
                    var.setTextContent(currentToken.spelling);
                    paragraph.appendChild(var);

                }
                else if(currentToken.kind == Token.CHARLITERAL ||
                        currentToken.kind == Token.INTLITERAL){
                    //Literales (caracteres y numerales): en color azul oscuro
                    Element var = document.createElement("font");
                    var.setAttribute("color","Blue");
                    var.setAttribute("size","3");
                    var.setAttribute("face","Courier");
                    var.setTextContent(currentToken.spelling);
                    paragraph.appendChild(var);

                }
                else if(currentToken.kind == -1){
                    //Comentarios: en color verde medio.
                    Element var = document.createElement("font");
                    var.setAttribute("color","Green");
                    var.setAttribute("size","3");
                    var.setAttribute("face","Courier");
                    var.setTextContent(currentToken.spelling);
                    paragraph.appendChild(var);

                }
                else if(currentToken.kind == -2){
                    //Espacios
                    Element var = document.createElement("font");
                    if(currentToken.spelling.length()==1){
                        var.setTextContent(" ");

                    }
                    else{
                        var.setTextContent("    ");

                    }
                    paragraph.appendChild(var);

                }
                else if(currentToken.kind == -3){ //salto de linea
                    Element var = document.createElement("font");
                    var.setTextContent("\n");
                    paragraph.appendChild(var);

                }
                else{
                    //Palabras reservadas color negro resaltado
                    Element var = document.createElement("font");
                    Element negrita = document.createElement("b");
                    negrita.setTextContent(currentToken.spelling);
                    var.setAttribute("color","Black");
                    var.setAttribute("size","3");
                    var.setAttribute("face","Courier");
                    var.appendChild(negrita);
                    paragraph.appendChild(var);

                }
                currentToken = lexicalAnalyser.scanCodeToHtml(); //Sigue escaneando
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
