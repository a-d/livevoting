package de.fuberlin.livevoting.server.domain.kind;

public abstract class EntityContainsPicture implements EntityDomain {

	abstract public String getPicture();
	
	private final int defaultWidth=200;
	private final int defaultHeight=100;
	private Integer width;
	private Integer height;
	
	public int getPictureHash() {
		return getPicture()!=null ? getPicture().hashCode() : -1;
	}
	
	public int getPictureWidth() {
		if(width==null) {
			width = getPictureStyleNum("width");
			if(width==null) {
				width = defaultWidth;
			}
		}
		return width;
	}

	public int getPictureHeight() {
		if(height==null) {
			height = getPictureStyleNum("height");
			if(height==null) {
				height = defaultHeight;
			}
		}
		return height;
	}

	public void setPictureHeight(int h) {
		height = h;
	}
	
	public void setPictureWidth(int w) {
		width = w;
	}
	
	public Integer getPictureStyleNum(String lookFor) {
		try {
			String lookup = getPicture().toLowerCase();
			lookFor += ":";
			int lookupPos = 0;
			if(lookup!=null && (lookupPos = lookup.indexOf(lookFor))>0) {
				lookup = lookup.substring(lookupPos+lookFor.length());
				String num = lookup.substring(0,lookup.indexOf("px"));
				return Integer.valueOf(num);
			}
		}
		catch(Exception e) {
			// not found
		}
		return null;
	}
}
