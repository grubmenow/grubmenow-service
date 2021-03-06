SELECT sqrt( 
           POWER((69.1 * (ZD2.LATTITUDE - ZD1.LATTITUDE)), 2) + 
           POWER((69.1 * (ZD2.LONGITUDE - ZD1.LONGITUDE) * cos(ZD1.LATTITUDE/57.3)), 2)
         ) AS DISTANCE
FROM ZIP_DATA ZD1, ZIP_DATA ZD2
WHERE ZD1.ZIP_CODE = '${zip_code1}'
AND ZD2.ZIP_CODE = '${zip_code2}'