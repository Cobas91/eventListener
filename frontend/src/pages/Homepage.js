/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import styled from 'styled-components'
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Typography,
} from '@mui/material'
import useStatistik from '../components/hooks/useStatistik'
import Chart from '../components/charts/Chart'
import { useState } from 'react'

export default function Homepage() {
  const { barChartData } = useStatistik()
  const [chartVariant, setChartVariant] = useState('SimpleBarChart')
  const handleChartChange = e => {
    setChartVariant(e.target.value)
  }
  return (
    <HomepageContainer>
      <Title variant="h4">Zapliance EventListener</Title>
      <StyledCard>
        <ul>
          <li>
            Im Event Listener können verschiedenste Events erstellt werden und
            mit Notification Usern und Aktionen verknüpft werden. Diese Events
            können anschließend über ein GET Request getriggert werden.
          </li>
          <li>
            Die Definierten Aktionen werden daraufhin automatisch gestartet und
            alle Notification User die zu diesem Event zugewiesen sind werden
            berücksichtigt.
          </li>
          <li>
            Unter dem MenüpunktApp User hinzufügen kann ein weiterer Login zur
            Applikation erstellt werden
          </li>
          <li>
            <Typography>Nützliche Links:</Typography>
            <ul>
              <li>
                <a
                  href={
                    window.location.origin.toString() + '/swagger-ui.html#/'
                  }
                >
                  REST Doku
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </StyledCard>
      <FormControl fullWidth>
        <InputLabel id="demo-simple-select-label">Variante</InputLabel>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={chartVariant}
          label="Variante"
          onChange={handleChartChange}
        >
          <MenuItem value="SimpleBarChart">BarChart</MenuItem>
          <MenuItem value="PieChart">PieChart</MenuItem>
        </Select>
      </FormControl>
      <Typography variant="h6">Ausgelöste Events</Typography>
      <Chart data={barChartData} variant={chartVariant} />
    </HomepageContainer>
  )
}

const StyledCard = styled.section`
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
  padding: 25px;
  margin: 60px;
  width: 80%;
`

const Title = styled(Typography)`
  margin: 10px;
`

const HomepageContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`
