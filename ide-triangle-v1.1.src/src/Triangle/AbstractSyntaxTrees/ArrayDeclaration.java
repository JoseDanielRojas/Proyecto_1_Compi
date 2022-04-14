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
public abstract class ArrayDeclaration extends AST {

  public ArrayDeclaration (SourcePosition thePosition) {
    super (thePosition);
    duplicated = false;
  }

  public boolean duplicated;
}
    

