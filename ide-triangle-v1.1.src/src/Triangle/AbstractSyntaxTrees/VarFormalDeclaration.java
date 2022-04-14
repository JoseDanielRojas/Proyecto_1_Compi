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
public class VarFormalDeclaration extends Declaration {

      public VarFormalDeclaration (TypeDenoter eAST,
                        SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
  }

  public Object visit (Visitor v, Object o) {
    return v.visitVarFormalDeclaration(this, o);
  }

  public TypeDenoter E;
    
}
