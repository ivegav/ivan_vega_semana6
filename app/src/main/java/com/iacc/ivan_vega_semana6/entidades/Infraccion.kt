package com.iacc.ivan_vega_semana6.entidades

data class Infraccion(
    val rutInspector: String,
    val nombreLocal: String,
    val direccion: String,
    val tipoInfraccion: String
){
    var folio: String = ""
}