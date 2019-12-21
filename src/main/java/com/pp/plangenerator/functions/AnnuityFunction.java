package com.pp.plangenerator.functions;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */

@FunctionalInterface
public interface AnnuityFunction<T, R> {
	R calculateAnnuity(T t1, T t2, T t3);
}
