package com.robindrew.trading.cityindex;

import static com.robindrew.trading.Instruments.AUD_USD;
import static com.robindrew.trading.Instruments.BITCOIN;
import static com.robindrew.trading.Instruments.DAX_30;
import static com.robindrew.trading.Instruments.DOW_JONES_30;
import static com.robindrew.trading.Instruments.ETHER;
import static com.robindrew.trading.Instruments.EUR_JPY;
import static com.robindrew.trading.Instruments.EUR_USD;
import static com.robindrew.trading.Instruments.FTSE_100;
import static com.robindrew.trading.Instruments.GBP_USD;
import static com.robindrew.trading.Instruments.LITECOIN;
import static com.robindrew.trading.Instruments.RIPPLE;
import static com.robindrew.trading.Instruments.SP_500;
import static com.robindrew.trading.Instruments.USD_CHF;
import static com.robindrew.trading.Instruments.USD_JPY;
import static com.robindrew.trading.Instruments.XAG_USD;
import static com.robindrew.trading.Instruments.XAU_USD;
import static com.robindrew.trading.cityindex.InstrumentCategory.DFT;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;

public class CityIndexInstrument extends Instrument implements ICityIndexInstrument {

	/** AUD/USD. */
	public static final CityIndexInstrument SPOT_AUD_USD = new CityIndexInstrument(400616153, DFT,  AUD_USD);
	/** EUR/JPY. */
	public static final CityIndexInstrument SPOT_EUR_JPY = new CityIndexInstrument(400616147, DFT, EUR_JPY);
	/** EUR/USD. */
	public static final CityIndexInstrument SPOT_EUR_USD = new CityIndexInstrument(400616149, DFT, EUR_USD);
	/** GBP/USD. */
	public static final CityIndexInstrument SPOT_GBP_USD = new CityIndexInstrument(400616150, DFT, GBP_USD);
	/** USD/CHF. */
	public static final CityIndexInstrument SPOT_USD_CHF = new CityIndexInstrument(400616148, DFT, USD_CHF);
	/** USD/JPY. */
	public static final CityIndexInstrument SPOT_USD_JPY = new CityIndexInstrument(400616151, DFT,  USD_JPY);

	/** Bitcoin. */
	public static final CityIndexInstrument SPOT_BITCOIN = new CityIndexInstrument(401379436, DFT, BITCOIN);
	/** Ether. */
	public static final CityIndexInstrument SPOT_ETHER = new CityIndexInstrument(401497467, DFT, ETHER);
	/** Ripple. */
	public static final CityIndexInstrument SPOT_RIPPLE = new CityIndexInstrument(401497526, DFT, RIPPLE);
	/** Litecoin. */
	public static final CityIndexInstrument SPOT_LITECOIN = new CityIndexInstrument(401497517, DFT, LITECOIN);

	/** FTSE 100. */
	public static final CityIndexInstrument SPOT_FTSE_100 = new CityIndexInstrument(400616113, DFT, FTSE_100);
	/** DAX. */
	public static final CityIndexInstrument SPOT_DAX = new CityIndexInstrument(400616115, DFT, DAX_30);
	/** S&amp;P 500. */
	public static final CityIndexInstrument SPOT_SP_500 = new CityIndexInstrument(400616141, DFT, SP_500);
	/** DOW JONES. */
	public static final CityIndexInstrument SPOT_DOW_JONES = new CityIndexInstrument(400616114, DFT, DOW_JONES_30);

	/** GOLD. */
	public static final CityIndexInstrument SPOT_GOLD = new CityIndexInstrument(400616377, DFT, XAU_USD);
	/** SILVER. */
	public static final CityIndexInstrument SPOT_SILVER = new CityIndexInstrument(400616380, DFT, XAG_USD);

	private final int marketId;
	private final IPricePrecision precision = new PricePrecision(2);
	private final InstrumentCategory type;

	public CityIndexInstrument(int marketId, InstrumentCategory type, IInstrument underlying) {
		super(String.valueOf(marketId), underlying);
		this.marketId = marketId;
		this.type = type;
	}

	public CityIndexInstrument(int marketId, InstrumentCategory type, InstrumentType instrumentType) {
		super(String.valueOf(marketId), instrumentType);
		this.marketId = marketId;
		this.type = type;
	}

	@Override
	public int getMarketId() {
		return marketId;
	}

	@Override
	public InstrumentCategory getCategory() {
		return type;
	}

	@Override
	public IPricePrecision getPrecision() {
		return precision;
	}

	@Override
	public ITradingProvider getProvider() {
		return TradingProvider.IGINDEX;
	}

}
