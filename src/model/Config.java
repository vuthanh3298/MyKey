package model;

import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KieuGo kieuGo;
	private boolean isChoPhepGoOaUy;
	private boolean isCtrlShift;
	public Config() {
		super();
	}
	
	public Config(KieuGo kieuGo, boolean isChoPhepGoOaUy, boolean isCtrlShift) {
		super();
		this.kieuGo = kieuGo;
		this.isChoPhepGoOaUy = isChoPhepGoOaUy;
		this.isCtrlShift = isCtrlShift;
	}

	public KieuGo getKieuGo() {
		return kieuGo;
	}

	public void setKieuGo(KieuGo kieuGo) {
		this.kieuGo = kieuGo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isChoPhepGoOaUy() {
		return isChoPhepGoOaUy;
	}
	public void setChoPhepGoOaUy(boolean isChoPhepGoOaUy) {
		this.isChoPhepGoOaUy = isChoPhepGoOaUy;
	}
	public boolean isCtrlShift() {
		return isCtrlShift;
	}
	public void setCtrlShift(boolean isCtrlShift) {
		this.isCtrlShift = isCtrlShift;
	}
	@Override
	public String toString() {
		return kieuGo + "";
	}
}
