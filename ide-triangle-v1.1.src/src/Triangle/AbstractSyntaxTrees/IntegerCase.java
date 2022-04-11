/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Pablo
 */
public class IntegerCase extends Cases {

    public IntegerCase(IntegerLiteral ilAST, SourcePosition thePosition) {
    super (thePosition);
    IL = ilAST;
  }

    @Override
    public Object visit(Visitor v, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public IntegerLiteral IL;
}
