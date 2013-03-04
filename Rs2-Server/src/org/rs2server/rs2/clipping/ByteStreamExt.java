package org.rs2server.rs2.clipping;

public final class ByteStreamExt {
	public byte[] buffer;
	public int currentOffset;

	public ByteStreamExt(byte[] abyte0) {
		this.buffer = abyte0;
		this.currentOffset = 0;
	}

	public int read3Bytes() {
		this.currentOffset += 3;
		return ((this.buffer[(this.currentOffset - 3)] & 0xFF) << 16)
				+ ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8)
				+ (this.buffer[(this.currentOffset - 1)] & 0xFF);
	}

	public byte[] readBytes() {
		int i = this.currentOffset;
		while (this.buffer[(this.currentOffset++)] != 10)
			;
		byte[] abyte0 = new byte[this.currentOffset - i - 1];
		System.arraycopy(this.buffer, i, abyte0, i - i, this.currentOffset - 1
				- i);
		return abyte0;
	}

	public void readBytes(int i, int j, byte[] abyte0) {
		for (int l = j; l < j + i; l++)
			abyte0[l] = this.buffer[(this.currentOffset++)];
	}

	public int readDWord() {
		this.currentOffset += 4;
		return ((this.buffer[(this.currentOffset - 4)] & 0xFF) << 24)
				+ ((this.buffer[(this.currentOffset - 3)] & 0xFF) << 16)
				+ ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8)
				+ (this.buffer[(this.currentOffset - 1)] & 0xFF);
	}

	public String readNewString() {
		int i = this.currentOffset;
		while (this.buffer[(this.currentOffset++)] != 0)
			;
		return new String(this.buffer, i, this.currentOffset - i - 1);
	}

	public long readQWord() {
		long l = readDWord() & 0xFFFFFFFF;
		long l1 = readDWord() & 0xFFFFFFFF;
		return (l << 32) + l1;
	}

	public int readR3Bytes() {
		this.currentOffset += 3;
		return ((this.buffer[(this.currentOffset - 1)] & 0xFF) << 16)
				+ ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8)
				+ (this.buffer[(this.currentOffset - 3)] & 0xFF);
	}

	public byte readSignedByte() {
		return this.buffer[(this.currentOffset++)];
	}

	public int readSignedWord() {
		this.currentOffset += 2;
		int i = ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8)
				+ (this.buffer[(this.currentOffset - 1)] & 0xFF);
		if (i > 32767)
			i -= 65536;
		return i;
	}

	public String readString() {
		int i = this.currentOffset;
		while (this.buffer[(this.currentOffset++)] != 10)
			;
		return new String(this.buffer, i, this.currentOffset - i - 1);
	}

	public int readUnsignedByte() {
		return this.buffer[(this.currentOffset++)] & 0xFF;
	}

	public int readUnsignedWord() {
		this.currentOffset += 2;
		return ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8)
				+ (this.buffer[(this.currentOffset - 1)] & 0xFF);
	}

	public void skip(int length) {
		this.currentOffset += length;
	}
}