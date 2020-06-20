package com.studioannwn.patterns.base;

import java.lang.reflect.TypeVariable;
import java.lang.reflect.InvocationTargetException;

import com.google.common.reflect.TypeToken;

import heronarts.lx.LX;
import heronarts.lx.LXModelComponent;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.model.LXModel;

public abstract class ModelPattern<M extends LXModel> extends LXPattern {
	protected M model;

	public ModelPattern(LX lx) {
		super(lx);

		setModel(lx.getModel());
	}

	/** Gets the model class, M. */
	public Class getModelClass() {
		TypeToken tt = new TypeToken<M>(getClass()) {};
		/*
		 * if it's a type variable, then ModelEffect was instantiated without a type
		 * parameter, so we just assume the effect works on all LXModels.
		 */
		if (tt.getType() instanceof TypeVariable) {
			return LXModel.class;
		}

		String modelClassName = tt.getType().getTypeName();
		String rawModelClassName = modelClassName.replaceAll("<.*", "");
		try {
			return Class.forName(rawModelClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** Gets an empty instance of the model class, M. */
	private M getEmptyModel() {
		Class c = getModelClass();
		M emptyModel;
		try {
			emptyModel = (M) c.getConstructor().newInstance();
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException
				| ClassCastException e) {
			throw new RuntimeException("Could not find a public default constructor for " + c.getName() + ": " + e);
		}
		return emptyModel;
	}

	public M getModel() {
		return model;
	}

	@Override
	public LXModelComponent setModel(LXModel model) {
		// M emptyModel = getEmptyModel();

		try {
			if (getModelClass().isAssignableFrom(model.getClass())) {
				this.model = (M) model;
			}
		} catch (ClassCastException e) {
			this.model = null;
		}

		return super.setModel(model);
	}
}
