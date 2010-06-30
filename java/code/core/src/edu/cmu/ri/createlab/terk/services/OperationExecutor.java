package edu.cmu.ri.createlab.terk.services;

import edu.cmu.ri.createlab.terk.expression.XmlOperation;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public interface OperationExecutor
   {
   Object executeOperation(final XmlOperation o);
   }