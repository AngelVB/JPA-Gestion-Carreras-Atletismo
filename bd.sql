-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- Servidor: localhost
-- Tiempo de generación: 02-02-2015 a las 13:12:04
-- Versión del servidor: 5.0.51
-- Versión de PHP: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Base de datos: `running`
-- 

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `carrera`
-- 

CREATE TABLE `carrera` (
  `id_carrera` int(11) NOT NULL auto_increment,
  `fecha` date NOT NULL,
  `poblacion` varchar(100) NOT NULL,
  `organiza` varchar(100) NOT NULL,
  `lugar_salida` varchar(100) NOT NULL,
  `distancia` decimal(10,2) NOT NULL,
  `modalidad` varchar(100) NOT NULL,
  PRIMARY KEY  (`id_carrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Volcar la base de datos para la tabla `carrera`
-- 


-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `corredor`
-- 

CREATE TABLE `corredor` (
  `id_corredor` int(11) NOT NULL auto_increment,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `fecha_nac` date NOT NULL,
  `club` varchar(255) NOT NULL,
  PRIMARY KEY  (`id_corredor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Volcar la base de datos para la tabla `corredor`
-- 


-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `marca`
-- 

CREATE TABLE `marca` (
  `id_marca` int(11) NOT NULL auto_increment,
  `tiempo` decimal(10,2) NOT NULL,
  `dorsal` int(11) NOT NULL,
  `id_corredor` int(11) default NULL,
  `id_carrera` int(11) default NULL,
  PRIMARY KEY  (`id_marca`),
  KEY `id_corredor` (`id_corredor`),
  KEY `id_carrera` (`id_carrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Volcar la base de datos para la tabla `marca`
-- 


-- 
-- Filtros para las tablas descargadas (dump)
-- 

-- 
-- Filtros para la tabla `marca`
-- 
ALTER TABLE `marca`
  ADD CONSTRAINT `marca_ibfk_1` FOREIGN KEY (`id_corredor`) REFERENCES `corredor` (`id_corredor`),
  ADD CONSTRAINT `marca_ibfk_2` FOREIGN KEY (`id_carrera`) REFERENCES `carrera` (`id_carrera`);
