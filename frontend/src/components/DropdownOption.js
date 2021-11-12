import * as React from 'react'
import { MenuItem, Select } from '@mui/material'

export default function DropdownOption({
  selectOptions,
  initialValue,
  onChange,
}) {
  return (
    <Select
      labelId="demo-simple-select-label"
      id="demo-simple-select"
      value={initialValue}
      label="Age"
      onChange={onChange}
    >
      {selectOptions ? (
        selectOptions.map(item => <MenuItem value={item}>{item}</MenuItem>)
      ) : (
        <></>
      )}
    </Select>
  )
}
