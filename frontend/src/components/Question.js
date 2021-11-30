/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from '@mui/material'

export default function Question({
  openState,
  setOpenState,
  handleYes,
  title,
  message,
}) {
  return (
    <Dialog
      open={openState}
      onClose={() => {
        setOpenState(false)
      }}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {message}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button
          onClick={() => {
            setOpenState(false)
          }}
        >
          Nein
        </Button>
        <Button
          onClick={() => {
            handleYes()
          }}
        >
          Ja
        </Button>
      </DialogActions>
    </Dialog>
  )
}
