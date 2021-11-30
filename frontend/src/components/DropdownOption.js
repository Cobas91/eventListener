/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
