/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author MSI
 */
public class ArrayDeclarationDOBLEDOT extends ArrayDeclaration{
      public ArrayDeclarationDOBLEDOT (IntegerLiteral eAST,TypeDenoter var,
                        SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
    C = var;
  }

    @Override
  public Object visit (Visitor v, Object o) {
    return v.visitArrayDeclarationDOBLEDOT(this, o);
  }

  public IntegerLiteral E;
  public TypeDenoter C;
    
}

