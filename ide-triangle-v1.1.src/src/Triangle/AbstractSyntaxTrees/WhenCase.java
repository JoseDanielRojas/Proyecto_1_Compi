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
public class WhenCase extends Cases {

    public WhenCase(Cases CaseLiAST,Cases CaseLiAST2, Command c1AST,
                    SourcePosition thePosition) {
    super (thePosition);
    CaseL = CaseLiAST;
    CaseL2=CaseLiAST2;
    C = c1AST;
  }

    @Override
    public Object visit(Visitor v, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Cases CaseL;
    public Cases CaseL2;
    public Command C;
}
