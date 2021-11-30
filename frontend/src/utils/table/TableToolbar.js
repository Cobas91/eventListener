/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import {
  GridToolbarColumnsButton,
  GridToolbarContainer,
  GridToolbarExport,
  GridToolbarFilterButton,
} from '@mui/x-data-grid'

export default function TableToolbar({ columnFilter, filter, csvExport }) {
  return (
    <GridToolbarContainer>
      {columnFilter ? <GridToolbarColumnsButton /> : <></>}
      {filter ? <GridToolbarFilterButton /> : <></>}
      {csvExport ? <GridToolbarExport /> : <></>}
    </GridToolbarContainer>
  )
}
