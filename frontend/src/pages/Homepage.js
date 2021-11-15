import * as React from 'react'
import styled from 'styled-components'
import { Typography } from '@mui/material'

export default function Homepage() {
  return (
    <HomepageContainer>
      <Title variant="h4">Zapliance EventListener</Title>
      <StyledCard>
        <Typography>
          <ul>
            <li>
              Im Event Listener können verschiedenste Events erstellt werden und
              mit Notification Usern und Aktionen verknüpft werden. Diese Events
              können anschließend über ein GET Request getriggert werden.
            </li>
            <li>
              Die Definierten Aktionen werden daraufhin automatisch gestartet
              und alle Notification User die zu diesem Event zugewiesen sind
              werden berücksichtigt.
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
        </Typography>
      </StyledCard>
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
