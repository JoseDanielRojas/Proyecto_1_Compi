package Triangle.Writer;

import Triangle.AbstractSyntaxTrees.Program;

import java.io.FileWriter;
import java.io.IOException;

public class XmlWriter {

    private String fileName;

    public XmlWriter(String fileName) {
        this.fileName = fileName;
    }

    public void write(Program ast){
        try {
            FileWriter fileWriter = new FileWriter(fileName);

            fileWriter.write("<?xml version=\"1.0\" standalone=\"yes\"?>\n");

            XmlWriterVisitor layout = new XmlWriterVisitor(fileWriter);
            ast.visit(layout, null);

            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }
    }
}
