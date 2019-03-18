package javaapplication24;

import java.util.ArrayList;

/**
 *
 * @author Dayana
 */
public class CentroSalud {
    String id ;
    String nombCentro;
    String parroquia;
    String canton;    
    String ciudad;
    String provincia;
    String pais;
    String area;
    String departamento;    
    ArrayList<String> cantEspecialistas;
    ArrayList<String> cantBloque;
    ArrayList<String> cantResiduos;
    String sector;
    String categoria;
    String tipo;
    ArrayList<String> especialidad;
    String anio;
    String fuente1;
    String fuente2;
    String codigo;
    String detalle;
    String residuos;
    String bloqueNombre;

    public CentroSalud() {
    }

    public CentroSalud(String id, String nombCentro, String parroquia, String canton, String ciudad, String provincia, String pais, String area, String departamento, ArrayList<String> cantEspecialistas, ArrayList<String> cantBloque, ArrayList<String> cantResiduos, String sector, String categoria, String tipo, ArrayList<String> especialidad, String anio, String fuente1, String fuente2, String codigo, String detalle, String residuos, String bloqueNombre) {
        this.id = id;
        this.nombCentro = nombCentro;
        this.parroquia = parroquia;
        this.canton = canton;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
        this.area = area;
        this.departamento = departamento;
        this.cantEspecialistas = cantEspecialistas;
        this.cantBloque = cantBloque;
        this.cantResiduos = cantResiduos;
        this.sector = sector;
        this.categoria = categoria;
        this.tipo = tipo;
        this.especialidad = especialidad;
        this.anio = anio;
        this.fuente1 = fuente1;
        this.fuente2 = fuente2;
        this.codigo = codigo;
        this.detalle = detalle;
        this.residuos = residuos;
        this.bloqueNombre = bloqueNombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombCentro() {
        return nombCentro;
    }

    public void setNombCentro(String nombCentro) {
        this.nombCentro = nombCentro;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public ArrayList<String> getCantEspecialistas() {
        return cantEspecialistas;
    }

    public void setCantEspecialistas(ArrayList<String> cantEspecialistas) {
        this.cantEspecialistas = cantEspecialistas;
    }

    public ArrayList<String> getCantBloque() {
        return cantBloque;
    }

    public void setCantBloque(ArrayList<String> cantBloque) {
        this.cantBloque = cantBloque;
    }

    public ArrayList<String> getCantResiduos() {
        return cantResiduos;
    }

    public void setCantResiduos(ArrayList<String> cantResiduos) {
        this.cantResiduos = cantResiduos;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(ArrayList<String> especialidad) {
        this.especialidad = especialidad;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getFuente1() {
        return fuente1;
    }

    public void setFuente1(String fuente1) {
        this.fuente1 = fuente1;
    }

    public String getFuente2() {
        return fuente2;
    }

    public void setFuente2(String fuente2) {
        this.fuente2 = fuente2;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getResiduos() {
        return residuos;
    }

    public void setResiduos(String residuos) {
        this.residuos = residuos;
    }

    public String getbloqueNombre() {
        return bloqueNombre;
    }

    public void setbloqueNombre(String bloqueNombre) {
        this.bloqueNombre = bloqueNombre;
    }

              
   }
