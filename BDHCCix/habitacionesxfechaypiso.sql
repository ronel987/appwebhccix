select * from habitacion h join habita_calendario hc on h.idhabitacion=hc.idhabitacion join calendario c on  hc.idcalendario=c.idcalendario where hc.estado='Disponible' and c.fecha='2022-11-16' and h.piso='3';
update habita_calendario hc join calendario c on hc.idcalendario=c.idcalendario set estado='OCUPADO' where hc.idhabitacion=1 and c.fecha='2022-11-16';
update habita_calendario hc join calendario c on hc.idcalendario=c.idcalendario set estado='DISPONIBLE' where hc.idhabitacion=1 and c.fecha='2022-11-16';