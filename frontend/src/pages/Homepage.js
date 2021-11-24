import * as React from 'react'
import styled from 'styled-components'
import { Typography } from '@mui/material'
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from 'recharts'
import useStatistik from '../components/hooks/useStatistik'

export default function Homepage() {
  const { barChartData } = useStatistik()

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
      <Typography variant="h6">Ausgelöste Events</Typography>
      <StatistikContainer>
        <BarChart
          width={1000}
          height={300}
          data={barChartData}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="amount" fill="var(--primary-blue)" />
        </BarChart>
      </StatistikContainer>
    </HomepageContainer>
  )
}
const StatistikContainer = styled.section`
  display: flex;
  justify-content: center;
  width: 50%;
`

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
