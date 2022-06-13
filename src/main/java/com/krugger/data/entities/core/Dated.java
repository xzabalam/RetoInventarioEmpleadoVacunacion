package com.krugger.data.entities.core;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xzabalam
 *
 */
public interface Dated extends Serializable {
	Date getFechaCreacion();

	Date getFechaModificacion();

	void setFechaCreacion(Date fechaCreacion);

	void setFechaModificacion(Date fechaModificacion);
}
