
public enum PNMFileType {
	PORTABLE_BITMAP ("P1"),
	PORTABLE_GRAYMAP ("P2"),
	PORTABLE_PIXMAP ("P3");
	
	private String tipo;
	PNMFileType(String tipo)
	{
		this.tipo = tipo;
		
	}
	
	public String getValue()
	{
		return tipo;
	}
}
