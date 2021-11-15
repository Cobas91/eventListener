import * as React from 'react'
import styled from 'styled-components'
import { Typography } from '@mui/material'
import logo from '../components/assets/logo_name.svg'

export default function Impressum() {
  return (
    <ImpressumContainer>
      <LogoBackground>
        <a href="/">
          <StyledImg src={logo} alt="logo" />
        </a>
      </LogoBackground>
      <TextContent>
        <Typography variant="h4">Impressum</Typography>
        <Typography>zapliance GmbH</Typography>
        <Typography>Holzdamm 57</Typography>
        <Typography>20099 Hamburg</Typography>
        <Typography>Germany</Typography>
        <Typography>Telefon: +49 (0) 40 5544 4490</Typography>
        <Typography>E-Mail: kontakt(a)zapliance.com</Typography>
        <Typography>Internet: www.zapliance.com</Typography>
        <Typography>USt-IdNr.: DE297892367</Typography>
        <Typography>
          Registergericht: Amtsgericht Hamburg, HRB 134758
        </Typography>
        <Typography>Geschäftsführer: Alexander Rühle</Typography>

        <Typography variant="h5">Rechtliche Hinweise</Typography>
        <Typography>Gewährleistung und Haftung:</Typography>
        <Typography>Keine Gewährleistung, keine Haftung.</Typography>
        <Typography>
          Die zapliance GmbH übernimmt keine Haftung für fehlerhafte, ungenaue
          oder unvollständige Information dieser Web-Seite oder für mögliche
          Folgen, die aus der Nutzung der Informationen resultieren.
        </Typography>
        <Typography>Kontaktdatennutzungsverbot:</Typography>
        <Typography>
          Der Nutzung von im Rahmen der Impressumspflicht veröffentlichten
          Kontaktdaten durch Dritte zur Übersendung von nicht ausdrücklich
          angeforderter Werbung und Informationsmaterialien wird hiermit
          ausdrücklich widersprochen. Die Betreiber der Seiten behalten sich
          ausdrücklich rechtliche Schritte im Falle der unverlangten Zusendung
          von Werbeinformationen, etwa durch Spam-Mails, vor.
        </Typography>
        <Typography variant="h5">Links</Typography>
        <Typography>
          Es ist möglich, mit Links über diese Webpräsenz zu anderen
          Internetseiten zu gelangen, die nicht von der zapliance GmbH
          unterhalten werden. Solche Internetseiten enthalten Informationen, die
          von Organisationen und natürlichen oder juristischen Personen, die von
          der zapliance GmbH rechtlich unabhängig sind, geschaffen,
          veröffentlicht, unterhalten oder anderweitig zur Verfügung gestellt
          werden. Die zapliance GmbH ist weder für den Inhalt dieser
          Internetseiten verantwortlich, noch billigt, unterstützt oder
          bestätigt sie Informationen, die auf externen Internetseiten oder
          darin angeführten gelinkten Adressen enthalten sind. Für externe Links
          zu fremden Inhalten können wir dabei trotz sorgfältiger inhaltlicher
          Kontrolle keine Haftung übernehmen.
        </Typography>
        <Typography variant="h5">Rechte am geistigen Eigentum</Typography>
        <Typography>
          Die zapliance GmbH behält sich alle Rechte am geistigen Eigentum der
          Informationen dieser Webseite vor. Dies gilt für jedes Webseiten
          Design, jeden Text, alle Grafiken, jede Auswahl bzw. jedes Layout.
          Dies gilt insbesondere für Urheberrechte, gleichgültig, ob sie
          speziell gekennzeichnet sind.
        </Typography>
        <Typography>
          Wir respektieren ausdrücklich Rechte am geistigen Eigentum anderer.
          Der hier erwähnte Name „SAP“ ist ein eingetragenes Markenzeichen der
          SAP SE.
        </Typography>
        <Typography>
          Bei den Texten dieser Webseite wurde zapliance von dem
          <a href="https://texterinhamburg.de">Hamburger Texter Gregor Engel</a>
          unterstützt.
        </Typography>
      </TextContent>
    </ImpressumContainer>
  )
}

const TextContent = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const ImpressumContainer = styled.section`
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
