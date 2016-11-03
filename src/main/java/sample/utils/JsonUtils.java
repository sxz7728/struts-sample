package sample.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class JsonUtils {
	private static ThreadLocal<Gson> gson = new ThreadLocal<Gson>() {
		protected synchronized Gson initialValue() {
			GsonBuilder gb = new GsonBuilder();
			gb.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
			gb.addSerializationExclusionStrategy(new ExclusionStrategy() {
				public boolean shouldSkipField(FieldAttributes fieldAttributes) {
					final Expose expose = fieldAttributes.getAnnotation(Expose.class);
					return expose != null && !expose.serialize();
				}

				public boolean shouldSkipClass(Class<?> aClass) {
					return false;
				}
			});
			gb.addDeserializationExclusionStrategy(new ExclusionStrategy() {
				public boolean shouldSkipField(FieldAttributes fieldAttributes) {
					final Expose expose = fieldAttributes.getAnnotation(Expose.class);
					return expose != null && !expose.deserialize();
				}

				public boolean shouldSkipClass(Class<?> aClass) {
					return false;
				}
			});
			return gb.create();
		}
	};

	public static String toJson(Object obj) {
		return gson.get().toJson(obj);
	}
}
