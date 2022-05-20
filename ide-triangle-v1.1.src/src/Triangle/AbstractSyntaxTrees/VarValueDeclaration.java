/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author arcan
 */
public class VarValueDeclaration extends Declaration {
    
    public VarValueDeclaration (Identifier iAST,Expression vAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        V = vAST;
    }

  public Object visit(Visitor v, Object o) {
    return v.visitVarValueDeclaration(this, o);
  }

  public Expression V;
  public Identifier I;
}
