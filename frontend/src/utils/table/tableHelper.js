/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

function getEventHeaders() {
  return [
    {
      field: 'id',
      headerName: 'ID',
      flex: 0.1,
      description: 'Entspricht der Indikatoren Nummer',
    },
    {
      field: 'actions',
      headerName: 'Aktionen',
      flex: 0.3,
      description:
        'Verfügbare Aktionen die ausgeführt werden können für das spezifische Event',
    },
    { field: 'name', headerName: 'Event Name', flex: 0.3 },
    { field: 'description', headerName: 'Beschreibung', flex: 1 },
  ]
}

function getUserHeaders() {
  return [
    {
      field: 'id',
      headerName: 'ID',
      flex: 0.2,
      hide: true,
    },
    { field: 'name', headerName: 'Name', flex: 0.3 },
    { field: 'email', headerName: 'E-Mail', flex: 1 },
  ]
}

function getFilters() {
  return {
    columnFilter: true,
    filter: true,
    csvExport: true,
  }
}

export { getEventHeaders, getUserHeaders, getFilters }
