package Entidad;

public class TipoMovimiento {
	private int idTipoMovimiento;
	private String DescripcionTipoMovimiento;
	
	public TipoMovimiento() {
		this.idTipoMovimiento = 0;
		this.DescripcionTipoMovimiento = "";
	}
	
	public TipoMovimiento(int idTipoMovimiento, String descripcionTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
		this.DescripcionTipoMovimiento = descripcionTipoMovimiento;
	}
	
	public int getIdTipoMovimiento() {
		return idTipoMovimiento;
	}
	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}
	public String getDescripcionTipoMovimiento() {
		return DescripcionTipoMovimiento;
	}
	public void setDescripcionTipoMovimiento(String descripcionTipoMovimiento) {
		DescripcionTipoMovimiento = descripcionTipoMovimiento;
	}

	@Override
	public String toString() {
		return "TipoMovimiento [idTipoMovimiento=" + idTipoMovimiento + ", DescripcionTipoMovimiento="
				+ DescripcionTipoMovimiento + "]";
	}
}
