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
