package org.rs2server.rs2.clipping;

/**
 * 
 * @author Josh Mai
 * 
 */
public class MemoryArchive {
	private ByteStream cache;
	private ByteStream index;
	private static final int INDEX_DATA_CHUNK_SIZE = 12;

	public MemoryArchive(ByteStream cache, ByteStream index) {
		this.cache = cache;
		this.index = index;
	}

	public int contentSize() {
		return this.index.length() / 12;
	}

	public byte[] get(int dataIndex) {
		try {
			if (this.index.length() < dataIndex * 12)
				return null;
			this.index.setOffset(dataIndex * 12);
			long fileOffset = this.index.getLong();
			int fileSize = this.index.getInt();
			this.cache.setOffset(fileOffset);
			byte[] buffer = this.cache.read(fileSize);
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}