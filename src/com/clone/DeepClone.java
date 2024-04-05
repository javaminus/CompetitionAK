package com.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepClone implements Serializable {

	private static final long serialVersionUID = -2658204965442453698L;

	protected Object deepClone() throws ClassNotFoundException, IOException {
        // 序列化
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oss = new ObjectOutputStream(bos);
		oss.writeObject(this);

        // 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

}
