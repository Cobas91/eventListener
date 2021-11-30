/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import styled from 'styled-components'
import logo from '../components/assets/logo_name.svg'
import { Typography } from '@mui/material'

export default function Datenschutz() {
  return (
    <DatenschutzContainer>
      <LogoBackground>
        <a href="/">
          <StyledImg src={logo} alt="logo" />
        </a>
      </LogoBackground>
      <Typography variant="h4">Datenschutz</Typography>
      <Typography>Datenschutz</Typography>
      <Typography>Datenschutz</Typography>
      <Typography>Datenschutz</Typography>
      <Typography>Datenschutz</Typography>
    </DatenschutzContainer>
  )
}

const DatenschutzContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: calc(100vh - 50px);
`

const LogoBackground = styled.div`
  position: relative;
  align-items: center;
  justify-content: center;
  display: flex;
  width: 100vw;
  height: 20vw;
  background-color: black;
  border: black solid;
  margin-bottom: 50px;
  border: 0;
`

const StyledImg = styled.img`
  width: 50vh;
`
